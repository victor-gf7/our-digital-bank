package br.com.joao.ourdigitalbank.controller.upload;


import br.com.joao.ourdigitalbank.controller.response.Response;
import br.com.joao.ourdigitalbank.controller.upload.dto.UploadRequest;
import br.com.joao.ourdigitalbank.model.client.Client;
import br.com.joao.ourdigitalbank.model.filecnh.FileCNH;
import br.com.joao.ourdigitalbank.service.client.ClientService;
import br.com.joao.ourdigitalbank.service.upload.UploadService;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/ourbank/upload")
public class UploadController {

    private final UploadService uploadService;
    private final ClientService clientService;

    public UploadController(UploadService uploadService, ClientService clientService) {
        this.uploadService = uploadService;
        this.clientService = clientService;
    }
    @PostMapping("/")
    public ResponseEntity<Response<UploadRequest>> saveFiles(@RequestParam MultipartFile frontFile, @RequestParam MultipartFile backFile, @RequestHeader Long clientId) throws Exception {

        Response<UploadRequest> response = new Response<>();
        UploadRequest uploadRequest = new UploadRequest();


        Client client =  clientService.findClient(clientId);

        if(client == null || client.getAddress() == null){
            response.addErrorMsgToResponse("There is no proposal associated with this step");
            return ResponseEntity.status(404).body(response);
        }

        uploadRequest.setFrontPath(uploadService.uploadImage(frontFile));
        uploadRequest.setBackPath(uploadService.uploadImage(backFile));

        FileCNH fileCNH = uploadRequest.convertDTOToEntity();
        FileCNH fileToCreate = uploadService.saveFiles(fileCNH, client);

        if(fileToCreate == null){
            response.addErrorMsgToResponse("Validation failed");
            return ResponseEntity.badRequest().body(response);
        }

        UploadRequest dtoSaved = fileToCreate.convertEntityToDTO();
        createSelfLink(fileToCreate,dtoSaved);

        response.setData(dtoSaved);

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Header Location", "/ourbank/proposal/client/");

        return new ResponseEntity<>(response, headers, HttpStatus.CREATED);

    }

    private void createSelfLink(FileCNH fileCNH, UploadRequest uploadRequest) {
        Link selfLink = WebMvcLinkBuilder.linkTo(UploadController.class).slash(fileCNH.getId()).withSelfRel();
        uploadRequest.add(selfLink);
    }

}

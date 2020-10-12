package br.com.joao.ourdigitalbank.controller.upload.dto;


import br.com.joao.ourdigitalbank.model.filecnh.FileCNH;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotBlank;

public class UploadRequest extends RepresentationModel<UploadRequest> {

    @NotBlank(message = "frontFile is a required attribute")
    private String frontPath;

    @NotBlank(message = "backFile is a required attribute")
    private String backPath;

    public String getFrontPath() {
        return frontPath;
    }

    public void setFrontPath(String frontPath) {
        this.frontPath = frontPath;
    }

    public String getBackPath() {
        return backPath;
    }

    public void setBackPath(String backPath) {
        this.backPath = backPath;
    }

    public FileCNH convertDTOToEntity() {
        return new ModelMapper().map(this, FileCNH.class);
    }
}

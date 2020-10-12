package br.com.joao.ourdigitalbank.service.upload;

import br.com.joao.ourdigitalbank.exception.InvalidFileFormatException;
import br.com.joao.ourdigitalbank.exception.InvalidFileSizeException;
import br.com.joao.ourdigitalbank.model.client.Client;
import br.com.joao.ourdigitalbank.model.filecnh.FileCNH;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadService {

    String uploadImage(MultipartFile file) throws IOException, InvalidFileFormatException, InvalidFileSizeException, AmazonServiceException, SdkClientException;

    FileCNH saveFiles(FileCNH fileCNH, Client client) throws Exception;
}

package br.com.joao.ourdigitalbank.model.filecnh;

import br.com.joao.ourdigitalbank.controller.upload.dto.UploadRequest;
import br.com.joao.ourdigitalbank.model.client.Client;
import net.minidev.json.annotate.JsonIgnore;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "file_cnh")
public class FileCNH {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    private String frontPath;

    @NotBlank
    private String backPath;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public UploadRequest convertEntityToDTO() {
        return new ModelMapper().map(this, UploadRequest.class);
    }
}

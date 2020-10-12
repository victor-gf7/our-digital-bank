package br.com.joao.ourdigitalbank.repository.upload;

import br.com.joao.ourdigitalbank.model.filecnh.FileCNH;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UploadRepository extends JpaRepository<FileCNH,Long> {
}

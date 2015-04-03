/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.parent;

//import entity.UploadedFile;
import entities.parent.PrimEntity;
//import files.FileManager;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
//import persistence.FileDao;
import dao.parent.Dao;
import support.AuthManager;
import support.ServiceResult;

/**
 *
 * @author Rice Pavel
 */
public class PrimService {
        
    protected Logger log = Logger.getLogger(this.getClass());
    
    @Autowired
    protected AuthManager authManager;
    
 

    protected <T extends PrimEntity> void _delete(Long id, Dao<T> dao) {
        T obj = dao.find(id);
        dao.deleteObj(obj);
    }
    
    protected <T extends PrimEntity> void _save(T ent, Dao<T> dao) {
        dao.save(ent);
    }
    
    protected <T extends PrimEntity> List<T> _getAll(Dao<T> dao) {
        return dao.getAll();
    }
    
     protected <T extends PrimEntity> void _update(T ent, Dao<T> dao) {
        dao.update(ent);
    }
     
     protected <T extends PrimEntity> void _delete(T ent, Dao<T> dao) {
        dao.delete(ent);
    }
     
     protected <T extends PrimEntity> T _find(Long id, Dao<T> dao) {
        return dao.find(id);
    }
    
    protected <T extends PrimEntity> void updateAndValidate(T ent, Dao<T> dao, ServiceResult res) {
        ServiceResult res1 = updateAndValidate(ent, dao);
        res.addErrors(res1.getErrors());
    }
    
    protected <T extends PrimEntity> void saveAndValidate(T ent, Dao<T> dao, ServiceResult res) {
        ServiceResult res1 = saveAndValidate(ent, dao);
        res.addErrors(res1.getErrors());
    }
    
     protected <T extends PrimEntity> ServiceResult saveAndValidate(T ent, Dao<T> dao) {
        ServiceResult res = new ServiceResult();
        PrimService.this.validate(res, ent);
        if (!res.hasErrors()) {
            dao.save(ent);
        }
        return res;
    }
     
    protected <T extends PrimEntity> ServiceResult updateAndValidate(T ent, Dao<T> dao) {
        ServiceResult res = new ServiceResult();
        PrimService.this.validate(res, ent);
        if (!res.hasErrors()) {
            dao.update(ent);
        }
        return res;
    }
        
    protected <T extends PrimEntity> void validate(ServiceResult res, T ent) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(ent);
        for (ConstraintViolation<T> viol : constraintViolations) {
            res.addError(viol.getMessage());
        }
    }
    
    protected  <T extends PrimEntity> boolean validate(T ent) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(ent);
        return constraintViolations.isEmpty();
    }
    
    /**
     * сохранить файл
     * @param fileEntity - объект entity, для хранения в базе данных информации о файле
     * @param file - объект файла
     * @throws IOException 
     */
    

}

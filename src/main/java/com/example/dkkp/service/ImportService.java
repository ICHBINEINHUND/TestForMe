package com.example.dkkp.service;

import com.example.dkkp.dao.ImportDao;
import com.example.dkkp.model.Import_Entity;
import java.time.LocalDateTime;
import java.util.List;

public class ImportService {
    private final ImportDao importDao;

    public ImportService() {
        this.importDao = new ImportDao();
    }

    public List<Import_Entity> getImportByCombinedCondition(LocalDateTime dateJoin, String typeDate, String id, String sortField, String sortOrder) {
        List<Import_Entity> result;

        if (dateJoin == null && id == null) {
            result = importDao.getAllImport();
        } else {
            result = null;
            List<List<Import_Entity>> conditions = List.of(
                    dateJoin != null ? importDao.getImportByDateImport(dateJoin, typeDate) : null,
                    id != null ? importDao.getImportByID(id) : null
            );

            for (List<Import_Entity> condition : conditions) {
                if (condition != null) {
                    if (result == null) {
                        result = condition;
                    } else {
                        result.retainAll(condition);
                    }
                }
            }
        }


        if (result != null && sortField != null && sortOrder != null) {
            result = importDao.sortResults(result, sortField, sortOrder);
        }
        return result != null ? result : List.of();
    }
}

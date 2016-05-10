package com.nairbspace.octoandroid.data.disk;

import com.nairbspace.octoandroid.data.db.PrinterDbEntity;
import com.nairbspace.octoandroid.data.db.PrinterDbEntityDao;

import javax.inject.Inject;

import de.greenrobot.dao.DaoException;

/** Convenience methods for db related info */
public class DbHelper {
    private final PrinterDbEntityDao mPrinterDbEntityDao;

    @Inject
    public DbHelper(PrinterDbEntityDao printerDbEntityDao) {
        mPrinterDbEntityDao = printerDbEntityDao;
    }

    public PrinterDbEntity getPrinterFromDbByName(String name) {
        PrinterDbEntity printerDbEntity;
        try {
            printerDbEntity = mPrinterDbEntityDao.queryBuilder()
                    .where(PrinterDbEntityDao.Properties.Name.eq(name))
                    .unique();
        } catch (DaoException e) {
            printerDbEntity = null;
        }

        return printerDbEntity;
    }

    public PrinterDbEntity getPrinterFromDbById(long printerId) {
        PrinterDbEntity printerDbEntity;
        try {
            printerDbEntity = mPrinterDbEntityDao.queryBuilder()
                    .where(PrinterDbEntityDao.Properties.Id.eq(printerId))
                    .unique();
        } catch (DaoException e) {
            printerDbEntity = null;
        }

        return printerDbEntity;
    }

    public void deleteOldPrinterInDb(PrinterDbEntity printerDbEntity) {
        PrinterDbEntity oldPrinterDbEntity = getPrinterFromDbByName(printerDbEntity.getName());
        if (oldPrinterDbEntity != null) {
            mPrinterDbEntityDao.delete(oldPrinterDbEntity);
        }
    }

    public void addPrinterToDb(PrinterDbEntity printerDbEntity) {
        mPrinterDbEntityDao.insertOrReplace(printerDbEntity);
    }
}
package org.framework.hsven.dataset.datatable.calcfun;


import org.framework.hsven.dataset.datatable.cache.QueryResult;

public class SetCalcFun {
    public static QueryResult and(QueryResult qr1, QueryResult qr2) {
        if (qr1 == qr2) {
            return qr1;
        }
        if (qr1.getDataTable() != qr2.getDataTable()) {
            return qr1;
        }
        int size = qr1.getCurrentSize();
        for (int jj = 0; jj < size; jj++) {
            if (qr1.selectedIndexArr[jj]) {
                if (!(qr2.selectedIndexArr[jj])) {
                    qr1.selectedIndexArr[jj] = false;
                    qr1.decrementSelected(1);
                }
            }
        }
        return qr1;
    }

    public static QueryResult andm(QueryResult qr1, QueryResult... qrArr) {
        if (qrArr.length == 0) {
            return qr1;
        }
        int size = qr1.getCurrentSize();
        int kk = 0;
        boolean isSub = false;
        for (int jj = 0; jj < size; jj++) {
            if (qr1.selectedIndexArr[jj]) {
                isSub = false;
                for (kk = 0; kk < qrArr.length; kk++) {
                    if (!(qrArr[kk].selectedIndexArr[jj])) {
                        isSub = true;
                        break;
                    }
                }
                if (isSub) {
                    qr1.selectedIndexArr[jj] = false;
                    qr1.decrementSelected(1);
                }
            }
        }
        return qr1;
    }


    public static QueryResult orm(QueryResult qr1, QueryResult... qrArr) {
        if (qrArr.length == 0) {
            return qr1;
        }
        int size = qr1.getCurrentSize();
        int kk = 0;
        boolean isAdd = false;

        for (int jj = 0; jj < size; jj++) {
            if (!(qr1.selectedIndexArr[jj])) {
                isAdd = false;
                for (kk = 0; kk < qrArr.length; kk++) {
                    if (qrArr[kk].selectedIndexArr[jj]) {
                        isAdd = true;
                        break;
                    }
                }
                if (isAdd) {
                    qr1.selectedIndexArr[jj] = true;
                    qr1.decrementSelected(-1);
                }
            }
        }
        return qr1;
    }


    public static QueryResult or(QueryResult qr1, QueryResult qr2) {
        if (qr1 == qr2) {
            return qr1;
        }
        if (qr1.getDataTable() != qr2.getDataTable()) {
            return qr1;
        }
        int size = qr1.getCurrentSize();

        for (int jj = 0; jj < size; jj++) {
            if (qr2.selectedIndexArr[jj]) {
                if (!(qr1.selectedIndexArr[jj])) {
                    qr1.selectedIndexArr[jj] = true;
                    qr1.decrementSelected(-1);
                }
            }
        }
        return qr1;
    }


    public static QueryResult not(QueryResult qr) {
        int size = qr.getCurrentSize();
        qr.selectedResultSize = 0;
        for (int jj = 0; jj < size; jj++) {
            qr.selectedIndexArr[jj] = !qr.selectedIndexArr[jj];
            if (qr.selectedIndexArr[jj]) {
                qr.decrementSelected(-1);
            }
        }
        return qr;
    }

    public static QueryResult eq(QueryResult qr) {
        int size = qr.getCurrentSize();
        qr.selectedResultSize = 0;
        for (int jj = 0; jj < size; jj++) {
            if (qr.selectedIndexArr[jj]) {
                qr.decrementSelected(-1);
            }
        }
        return qr;
    }

}

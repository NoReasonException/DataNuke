package com.noreasonexception.loadable.base.etc;

import java.util.Date;

public class LoopOperationStatus {
    private LoopOperationResult status;
    private Throwable error;
    private Double value;
    private Date   date;

    private LoopOperationStatus(Double value,LoopOperationResult status, Throwable error){
        this.value=value;
        this.status =status;
        this.error=error;
        this.date=new Date();
    }
    public static LoopOperationStatus buildSuccess(Double b){
        return new LoopOperationStatus(b,LoopOperationResult.Success,null);
    }
    public static LoopOperationStatus buildSameValueSituation(Double b){
        return new LoopOperationStatus(b,LoopOperationResult.SameValueSituation,null);
    }

    public static LoopOperationStatus buildExceptionThrown(Throwable e){
        return new LoopOperationStatus(null,LoopOperationResult.ExceptionThrown,e);
    }

    public LoopOperationResult getStatus() {
        return status;
    }

    public Throwable getError() {
        return error;
    }

    public Double getValue() { return value; }

    public Date getCreationDate() { return date; }
}

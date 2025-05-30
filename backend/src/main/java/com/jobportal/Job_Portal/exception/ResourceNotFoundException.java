package com.jobportal.Job_Portal.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resourceName,String fieldName,Object fieldValue){
        super(resourceName + "not found with " + fieldName + "=" + fieldValue);
    }



}

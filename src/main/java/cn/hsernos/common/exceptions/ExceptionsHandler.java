package cn.hsernos.common.exceptions;

import cn.hsernos.common.beans.ResultBean;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartException;

@ControllerAdvice
@CrossOrigin
@RestController
public class ExceptionsHandler {


    @ExceptionHandler(MultipartException.class)
    public ResultBean customGenericExceptionHnadler(MultipartException exception) {
        return new ResultBean("文件不能超过10M", ResultBean.FAIL);
    }

    @ExceptionHandler(NoPermissionException.class)
    public ResultBean customGenericExceptionHnadler(NoPermissionException exception) {
        return new ResultBean(exception.getLocalizedMessage(), ResultBean.NO_PERMISSION);
    }

    @ExceptionHandler(CheckException.class)
    public ResultBean customGenericExceptionHnadler(CheckException exception) {
        return new ResultBean(exception.getLocalizedMessage(), ResultBean.FAIL);
    }

    @ExceptionHandler(UnloginException.class)
    public ResultBean customGenericExceptionHnadler(UnloginException exception) {
        return new ResultBean(exception.getLocalizedMessage(), ResultBean.NO_LOGIN);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResultBean customGenericExceptionHnadler(RuntimeException exception) {
        return new ResultBean(exception.getLocalizedMessage(), ResultBean.FAIL);
    }

}
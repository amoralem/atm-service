package com.ibm.atm.error;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.ibm.atm.exception.DataExcepcion;
import com.ibm.atm.exception.ServiceClientErrorExcepcion;
import com.ibm.atm.model.ErrorDescripcion;
import com.ibm.atm.model.ErrorDetalle;
import com.ibm.atm.util.LogHandler;
import com.ibm.atm.util.MessageExceptionHandler;

@ControllerAdvice
@RestController
public class ErrorResponseEntityExceptionHandler {
	
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public final ResponseEntity<ErrorDetalle> handleNotReadable(HttpMessageNotReadableException ex, WebRequest request) {
		LogHandler.error(null, getClass(), "Error del cliente, envió mal los datos ", ex);
		ErrorDetalle details = this.crearErrorDetalle(MessageExceptionHandler.COD00001, 
				MessageExceptionHandler.DESCRIPTION_NOTREADABLE,
				MessageExceptionHandler.DETAIL_INPUT_DATA_ERROR, 
				MessageExceptionHandler.MESSAGE_ERROR_VALID,
				MessageExceptionHandler.NIVEL_ERROR);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(details);
	}	
	
	@ExceptionHandler(ServiceClientErrorExcepcion.class)
	public final ResponseEntity<ErrorDetalle> handleServiceClientErrorExcepcion(ServiceClientErrorExcepcion ex, WebRequest request) {
		LogHandler.error(null, getClass(), "Ocurrio un error de comuicacion con el servicio ", ex);
		ErrorDetalle details = this.crearErrorDetalle(MessageExceptionHandler.COD00002, 
				MessageExceptionHandler.DESCRIPTION_TRY_AGAIN_LATER,
				ex.getMessage(),
				MessageExceptionHandler.MESSAGE_ERROR_SERVICE,
				MessageExceptionHandler.NIVEL_ERROR);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(details);
	}
	
	@ExceptionHandler(DataExcepcion.class)
	public final ResponseEntity<ErrorDetalle> handleDataExcepcion(DataExcepcion ex, WebRequest request) {
		LogHandler.error(null, getClass(), "Ocurrio un error de validacion ", ex);
		ErrorDetalle details = this.crearErrorDetalle(MessageExceptionHandler.COD00003, 
				MessageExceptionHandler.DESCRIPTION_TRY_AGAIN_LATER,
				ex.getMessage(),
				MessageExceptionHandler.MESSAGE_ERROR_BUSINESS,
				MessageExceptionHandler.NIVEL_ERROR);
		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(details);
	}
		
	private ErrorDetalle crearErrorDetalle(String codigo, String descripcion, String detalle, String mensaje,
			String nivel) {
		ErrorDetalle errorDetails = new ErrorDetalle();
		ErrorDescripcion detail = new ErrorDescripcion();
		detail.setCodigo(codigo);
		detail.setDescripcion(descripcion);
		detail.setDetalle(detalle);
		detail.setNivel(nivel);
		detail.setMensaje(mensaje);
		List<ErrorDescripcion> errors = new ArrayList<>();
		errors.add(detail);
		errorDetails.setErrors(errors);
		return errorDetails;
	}

}

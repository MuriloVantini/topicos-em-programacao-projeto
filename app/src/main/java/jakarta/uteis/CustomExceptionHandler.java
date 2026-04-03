package jakarta.uteis;

import java.io.IOException;
import java.util.Iterator;

import jakarta.faces.FacesException;
import jakarta.faces.application.NavigationHandler;
import jakarta.faces.application.ViewExpiredException;
import jakarta.faces.context.ExceptionHandler;
import jakarta.faces.context.ExceptionHandlerWrapper;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ExceptionQueuedEvent;
import jakarta.faces.event.ExceptionQueuedEventContext;

public class CustomExceptionHandler extends ExceptionHandlerWrapper {

	private final ExceptionHandler wrapped;

	public CustomExceptionHandler(ExceptionHandler wrapped) {
		this.wrapped = wrapped;
	}

	@Override
	public ExceptionHandler getWrapped() {
		return this.wrapped;
	}

	@Override
	public void handle() throws FacesException {
		Iterator<ExceptionQueuedEvent> iterator = getUnhandledExceptionQueuedEvents().iterator();

		while (iterator.hasNext()) {
			ExceptionQueuedEvent event = iterator.next();
			Throwable exception = ((ExceptionQueuedEventContext) event.getSource()).getException();
			ViewExpiredException viewExpired = findViewExpired(exception);

			if (viewExpired != null) {
				FacesContext context = FacesContext.getCurrentInstance();
				if (context != null && !context.getResponseComplete()) {
					ExternalContext external = context.getExternalContext();
					try {
						String target = external.getRequestContextPath() + "/menuprincipal.xhtml";
						external.redirect(target);
						context.responseComplete();
					} catch (IOException e) {
						throw new FacesException(e);
					}
				}
				iterator.remove();
			}
		}

		getWrapped().handle();
	}

	private ViewExpiredException findViewExpired(Throwable throwable) {
		while (throwable != null) {
			if (throwable instanceof ViewExpiredException) {
				return (ViewExpiredException) throwable;
			}
			throwable = throwable.getCause();
		}
		return null;
	}
}

package interceptors;

import play.libs.F;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;
import utils.ExceptionMailer;

public class CatchAction extends Action.Simple {

	@Override
	public F.Promise<Result> call(Http.Context ctx) throws Throwable {

		try {
			ExceptionMailer.log( ctx.request().method() + "\t" + ctx.request().path());
			
			return delegate.call(ctx);
		} catch(Throwable e) {
			ExceptionMailer.send(e);
			throw new RuntimeException(e);
		}
	}

}

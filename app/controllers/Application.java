package controllers;

import play.data.validation.Required;
import play.mvc.Controller;
import play.mvc.With;

@With(MenuInjector.class)
public class Application extends Controller {

    public static void index() {
        render();
    }

    public static void test() {
    	render();
    }
    
    public static void sayHello (@Required String myname) {
    	if (validation.hasErrors()) {
    		flash.error("Fehler: Bitte Namen angeben!");
    		index();
    	}
    	render(myname);
    }
}
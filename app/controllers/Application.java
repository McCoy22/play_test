package controllers;

import java.util.List;

import net.sf.cglib.transform.impl.AddDelegateTransformer;

import models.Post;
import play.Play;
import play.data.validation.Required;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;

@With(MenuInjector.class)
public class Application extends Controller {

	public static void index() {
		Post frontPost = Post.find("order by postedAt desc").first();
		List<Post> olderPosts = Post.find("order by postedAt desc").from(1).fetch(10);
		render(frontPost, olderPosts);
	}
	
	@Before
	public static void addDefaults () {
	    renderArgs.put("blogTitle", Play.configuration.getProperty("blog.title"));
	    renderArgs.put("blogBaseline", Play.configuration.getProperty("blog.baseline"));
	}

	public static void test() {
		render();
	}

	public static void show (Long id) {
		Post post = Post.findById(id);
		render (post);
	}
	
	public static void sayHello(@Required String myname) {
		if (validation.hasErrors()) {
			flash.error("Fehler: Bitte Namen angeben!");
			index();
		}
		render(myname);
	}
	
	public static void postComment(Long postId, @Required String author, @Required String content) {
	    Post post = Post.findById(postId);
	    if (validation.hasErrors()) {
	        render("Application/show.html", post);
	    }
	    post.addComment(author, content);
	    flash.success("Thanks for posting %s", author);
	    show(postId);
	}
}
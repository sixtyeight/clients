package at.metalab.m68k.soup;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import at.metalab.m68k.soup.resource.Blog;
import at.metalab.m68k.soup.resource.User;

public class SoupHelper {

	public static Blog getOwnSoup(User user) {
		for (Blog blog : user.getBlogs()) {
			if (blog.getPermissions().contains("owner")) {
				return blog;
			}
		}
		return null; // this should not happen
	}

	public static List<Blog> getGroups(User user) {
		Blog ownSoup = SoupHelper.getOwnSoup(user);

		List<Blog> groups = new ArrayList<Blog>();
		for (Blog blog : user.getBlogs()) {
			if (Comparators.Group.BY_NAME.compare(blog, ownSoup) == 0) {
				continue;
			}
			groups.add(blog);
		}
		return groups;
	}

	public static Blog findByUrl(User user, String url) {
		for (Blog blog : user.getBlogs()) {
			if (blog.getResource().equals(url)) {
				return blog;
			}
		}
		return null;
	}

	public static List<Blog> findByName(User user, String regex) {
		List<Blog> blogs = new ArrayList<Blog>();

		for (Blog blog : user.getBlogs()) {
			if (blog.getName().matches(regex)) {
				blogs.add(blog);
			}
		}

		return blogs;
	}

	public final static class Comparators {
		public final static class Group {
			public final static Comparator<Blog> BY_NAME = new Comparator<Blog>() {
				public int compare(Blog o1, Blog o2) {
					return o1.getName().compareTo(o2.getName());
				}
			};

			public final static Comparator<Blog> BY_RESOURCE = new Comparator<Blog>() {
				public int compare(Blog o1, Blog o2) {
					return o1.getResource().compareTo(o2.getResource());
				}
			};

		}
	}

}

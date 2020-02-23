/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab7.analytics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lab7.entities.Comment;
import lab7.entities.Post;
import lab7.entities.User;

/**
 *
 * @author harshalneelkamal
 */
public class AnalysisHelper {
    // find user with Most Likes
    //  key: UserId ; Value: sum of likes from all comments
    public void userWithMostLikes() {
        Map<Integer, Integer> userLikesCount = new HashMap<>();
        Map<Integer, User> users = DataStore.getInstance().getUsers();
    
        for (User user : users.values()) {
            for (Comment c : user.getComments()) {
                int likes = 0;
                if (userLikesCount.containsKey(user.getId())) {
                    likes = userLikesCount.get(user.getId());
                }
                likes += c.getLikes();
                userLikesCount.put(user.getId(), likes);
            }
        }
        int max = 0;
        int maxId = 0;
        for (int id : userLikesCount.keySet()) {
            if (userLikesCount.get(id) > max) {
                max = userLikesCount.get(id);
                maxId = id;
            }
        }
        System.out.println("User with most likes: " + max + "\n" 
            + users.get(maxId));
    }
    
    // find 5 comments which have the most likes
    public void getFiveMostLikedComment() {
        Map<Integer, Comment> comments = DataStore.getInstance().getComments();
        List<Comment> commentList = new ArrayList<>(comments.values());
        
        Collections.sort(commentList, new Comparator<Comment>() {
            @Override 
            public int compare(Comment o1, Comment o2) {
                return o2.getLikes() - o1.getLikes();
            }
        });
        
        System.out.println("5 most likes comments: ");
        for (int i = 0; i < commentList.size() && i < 5; i++) {
            System.out.println(commentList.get(i));
        }
    }
    
    //find average number of likes per comment
    public void getAverageLikesPerComment() {
        Map<Integer, Comment> comments = DataStore.getInstance().getComments();
        int totalLikes = 0;
        int commentNum = comments.size();
        
        for (Comment c : comments.values()) {
                totalLikes += c.getLikes();
        }
        
        System.out.println("Average number of likes per comment: ");
        System.out.println(totalLikes/commentNum);
    }
    
    //find the post with most liked comments
    public void getPostWithMostLikedComments() {
        Map<Integer, Post> posts = DataStore.getInstance().getPosts();
        int likesNum = 0;
        int mostLikesNum = 0;
        int mostLikesId = 0;
        
        for (Post p : posts.values()) {
            likesNum = 0;
            for (Comment c : p.getComments()) {
                likesNum += c.getLikes();
            }
            
            if (likesNum > mostLikesNum) {
                mostLikesNum = likesNum;
                mostLikesId = p.getPostId();
            }
        }
        
        System.out.println("The post with most liked comments: ");
        System.out.println(mostLikesId);
    }
    
     //find the post with most comments
    public void getPostWithMostComments() {
        Map<Integer, Post> posts = DataStore.getInstance().getPosts();
        int mostCommentsNum = 0;
        int mostCommentsId = 0;
        
        for (Post p : posts.values()) {      
            if (p.getComments().size() > mostCommentsNum) {
                mostCommentsNum = p.getComments().size();
                mostCommentsId = p.getPostId();
            }
        }
        
        System.out.println("The post with most liked comments: ");
        System.out.println(mostCommentsId);
    }
    //4). Top 5 inactive users based on total posts number.
    static public void topInactiveFiveUsersBasedOnTotalPostsNumber() {
        Map<User, Integer> userPostCount = new HashMap<>();
        Map<Integer, User> users = DataStore.getInstance().getUsers();
        Map<Integer, Post> posts = DataStore.getInstance().getPosts();
        for (User user : users.values()) {
            int postNumber = 0;
            for (Post post : posts.values()) {
                if (userPostCount.containsKey(user)) {
                    postNumber = userPostCount.get(user);
                }
                if (post.getUserId() == user.getId()) {
                    postNumber += 1;
                    userPostCount.put(user, postNumber);
                }

            }
        }
        System.out.println("The top 5 inactive user based on total posts number");
        for (int i = 0; i < 5; i++) {
            int currentMin = 100000;
            User maxUser = null;
            for (User user : userPostCount.keySet()) {
                if (userPostCount.get(user) < currentMin) {
                    currentMin = userPostCount.get(user);
                    maxUser = user;
                }
            }
            System.out.println(maxUser + " " + currentMin);
            userPostCount.remove(maxUser);
        }
    }

    //5). Top 5 inactive users based on total comments they created.
    static public void topInactiveFiveUsersBasedOnTotalComments() {
        Map<User, Integer> userCommentCount = new HashMap<>();
        Map<Integer, User> users = DataStore.getInstance().getUsers();
        Map<Integer, Comment> comments = DataStore.getInstance().getComments();
        for (User user : users.values()) {
            int commentNumber = 0;
            for (Comment comment : comments.values()) {
                if (userCommentCount.containsKey(user)) {
                    commentNumber = userCommentCount.get(user);
                }
                if (comment.getUserId() == user.getId()) {
                    commentNumber += 1;
                    userCommentCount.put(user, commentNumber);
                }

            }
        }
        System.out.println("The top 5 inactive user based on total comment created number");
        for (int i = 0; i < 5; i++) {
            int currentMin = 100000;
            User maxUser = null;
            for (User user : userCommentCount.keySet()) {
                if (userCommentCount.get(user) < currentMin) {
                    currentMin = userCommentCount.get(user);
                    maxUser = user;
                }
            }
            System.out.println(maxUser + " " + currentMin);
            userCommentCount.remove(maxUser);
        }
    }
    
    //Top 5 inactive users overall (sum of comments, posts and likes)
    public void topInactiveFiveUserBasedOnOverall() {
        Map<User, Integer> userCount = new HashMap<>();
        Map<Integer, User> users = DataStore.getInstance().getUsers();
        Map<Integer, Comment> comments = DataStore.getInstance().getComments();
        Map<Integer, Post> posts = DataStore.getInstance().getPosts();
        for (User user : users.values()) {
            int commentNumber = 0;
            for (Comment comment : comments.values()) {
                if (userCount.containsKey(user)) {
                    commentNumber = userCount.get(user);
                }
                if (comment.getUserId() == user.getId()) {
                    commentNumber += 1;
                    commentNumber += comment.getLikes();
                    userCount.put(user, commentNumber);
                }
            }
        }
        for (User user : users.values()) {
            int postNumber = 0;
            for (Post post : posts.values()) {
                if (userCount.containsKey(user)) {
                    postNumber = userCount.get(user);
                }
                if (post.getUserId() == user.getId()) {
                    postNumber += 1;
                    userCount.put(user, postNumber);
                }

            }
        }
        System.out.println("The top 5 inactive user based on overall");
        for (int i = 0; i < 5; i++) {
            int currentMin = 100000;
            User maxUser = null;
            for (User user : userCount.keySet()) {
                if (userCount.get(user) < currentMin) {
                    currentMin = userCount.get(user);
                    maxUser = user;
                }
            }
            System.out.println(maxUser + " " + currentMin);
            userCount.remove(maxUser);
        }
    }
    //Top 5 proactive users overall (sum of comments, posts and likes)
    public void topPoractiveFiveUserBasedOnOverall() {
        Map<User, Integer> userCount = new HashMap<>();
        Map<Integer, User> users = DataStore.getInstance().getUsers();
        Map<Integer, Comment> comments = DataStore.getInstance().getComments();
        Map<Integer, Post> posts = DataStore.getInstance().getPosts();
        for (User user : users.values()) {
            int commentNumber = 0;
            for (Comment comment : comments.values()) {
                if (userCount.containsKey(user)) {
                    commentNumber = userCount.get(user);
                }
                if (comment.getUserId() == user.getId()) {
                    commentNumber += 1;
                    commentNumber += comment.getLikes();
                    userCount.put(user, commentNumber);
                }
            }
        }
        for (User user : users.values()) {
            int postNumber = 0;
            for (Post post : posts.values()) {
                if (userCount.containsKey(user)) {
                    postNumber = userCount.get(user);
                }
                if (post.getUserId() == user.getId()) {
                    postNumber += 1;
                    userCount.put(user, postNumber);
                }

            }
        }
        System.out.println("The top 5 proactive user based on overall");
        for (int i = 0; i < 5; i++) {
            int currentMin = 0;
            User maxUser = null;
            for (User user : userCount.keySet()) {
                if (userCount.get(user) > currentMin) {
                    currentMin = userCount.get(user);
                    maxUser = user;
                }
            }
            System.out.println(maxUser + " " + currentMin);
            userCount.remove(maxUser);
        }
    }
}

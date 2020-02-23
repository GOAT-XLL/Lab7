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
}

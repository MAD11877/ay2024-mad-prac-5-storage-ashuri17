package sg.edu.np.mad.madpractical5;

public class User {
    private String name;
    private String description;
    private int id;
    private boolean followed;
    public void setName(String username){
        this.name = username;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public void setId(int id){
        this.id = id;
    }
    public void setFollowed(boolean followed){
        this.followed = followed;
    }
    public String getName(){
        return name;
    }
    public String getDescription(){
        return description;
    }
    public boolean getFollowed() {return followed;}
    public int getId() {return id;}
    public User(String name, String description, int id, boolean followed){
        this.name = name;
        this.description = description;
        this.id = id;
        this.followed = followed;
    }
}

package higo.com.bean;

/**
 * Created by user on 2017/8/26.
 */

public class TodoItem {
    private Integer id;
    private String title;
    private String createtime;
    public TodoItem(Integer id,String title,String createtime ){
        super();
        this.id=id;
        this.title=title;
        this.createtime=createtime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }
}

package com.bjtu.testbox.tools.model;

import com.bjtu.testbox.config.constant.Status;
import com.bjtu.testbox.entity.Box;

import java.util.ArrayList;
import java.util.List;

class BoxSub{
    // 编号
    private String text;
    // ID
    private int id;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "BoxSub{" +
                "text='" + text + '\'' +
                ", id=" + id +
                "}\n";
    }
}

class BoxGroup{
    // 试验箱类型名字
    private String text;
    // 属于类型text的试验箱集合
    private List<BoxSub> children = new ArrayList<>();

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<BoxSub> getChildren() {
        return children;
    }

    public void setChildren(List<BoxSub> children) {
        this.children = children;
    }

    public void addBox(BoxSub boxSub){
        this.children.add(boxSub);
    }

    @Override
    public String toString() {
        return "BoxGroup{" +
                "text='" + text + '\'' +
                ", children=" + children +
                "}\n";
    }
}


public class BoxOption {

    public BoxOption(){
        int type[] = new int[]{
                Status.BOX_TYPE_DC_CROSSROAD,
                Status.BOX_TYPE_AC_CROSSROAD,
                Status.BOX_TYPE_ELECTRICITY
        };
        // 添加3个组
        for (int i = 0; i < type.length; i++) {
            BoxGroup boxGroup = new BoxGroup();
            boxGroup.setText(Status.BOX_TYPE.get(type[i]));
            addBoxGroup(boxGroup);
        }
    }

    private List<BoxGroup> results = new ArrayList<>();

    public List<BoxGroup> getResults() {
        return results;
    }

    public void setResults(List<BoxGroup> results) {
        this.results = results;
    }

    public void addBoxGroup(BoxGroup bg){
        this.results.add(bg);
    }

    public BoxOption convert(List<Box> boxes){
        for (Box box : boxes) {
            BoxSub bs = new BoxSub();
            bs.setId(box.getBoxId());
            bs.setText(box.getBoxNumber());
            String type = Status.BOX_TYPE.get(box.getBoxType());
            for (BoxGroup result : results) {
                if (result.getText().equals(type)){
                    result.addBox(bs);
                    break;
                }
            }
        }
        return this;
    }

    @Override
    public String toString() {
        return "BoxOption{" +
                "results=" + results +
                "}\n";
    }
}

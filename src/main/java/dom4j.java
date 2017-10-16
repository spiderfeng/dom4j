import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.dom4j.tree.DefaultElement;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

/**
 * Created by fqlive on 2017/10/16.
 * getName()得到所有的name元素的值
 * getSingleName()得到第一个name元素的值
 * getSecondName()得到第二个name元素的值
 * addElement1()在第一个p1后面添加新节点<sex>女</sex>
 * 在addSchloolSec()方法，在第一个p1里面的age前面添加一个<school>sust.edu.cn</school>节点
 * modifySchool()修改学校为aust
 * delSchool()删除school节点
 */
public class dom4j {
    public static void main(String[] args) throws Exception {
        SAXReader saxReader=new SAXReader();
        Document document=saxReader.read("src/1.xml");
        getName(document);
        getSingleName(document);
        getSecondName(document);
        addElement1(document);
        addSchoolSec(document);
        modifySchool(document);
        delSchool(document);
    }

    private static void delSchool(Document document) throws Exception {   //删除school节点
        Element element=document.getRootElement();
        Element p1=element.element("p1");
        Element school=p1.element("school");
         p1.remove(school);
         //回写
        xmlWriters("src/1.xml",document);
    }

    private static void modifySchool(Document document) throws Exception {     //修改第一个p1里面school的值为aust
        Element element=document.getRootElement();
         Element p1=element.element("p1");
         Element school=p1.element("school");
         school.setText("aust");
        //回写操作
        xmlWriters("src/1.xml",document);
    }

    private static void addSchoolSec(Document document) throws Exception {  //在第一个p1标签的第二个元素位置处添加<school>aust.edu.cn</school>
        Element element=document.getRootElement();
        Element p1=element.element("p1");
        List <Element> list=p1.elements();
        Element school= DocumentHelper.createElement("school");
        school.addText("aust.edu.cn");
        list.add(1,school);
        xmlWriters("src/1.xml",document);
    }

    private static void addElement1(Document document) throws Exception {    //添加<sex>女<sex>元素节点
        Element element=document.getRootElement();
        Element p1=element.element("p1");
        Element element2=p1.addElement("sex");
        element2.addText("女");
        //按照格式重新回写
         org.dom4j.io.OutputFormat outputFormat= org.dom4j.io.OutputFormat.createPrettyPrint();//漂亮的显示方式
        //org.dom4j.io.OutputFormat outputFormat1= org.dom4j.io.OutputFormat.createCompactFormat();//压缩在一起的显示方式
          XMLWriter xmlWriter=new XMLWriter(new FileOutputStream("src/1.xml"),outputFormat);
          xmlWriter.write(document);
          xmlWriter.close();
    }

    private static void getSecondName(Document document) {     //得到第二个p1节点的name的元素值
        Element element=document.getRootElement();
          List <Element> list= element.elements("p1");
          Element element1=list.get(1);   //下标从0开始
          Element element2=element1.element("name");
        System.out.println("第二个p1内部name的元素值为:"+element2.getText());

    }

    private static void getSingleName(Document document) {   //得到第一个p1节点的name的元素值
        Element element=document.getRootElement();
        Element ele1=element.element("p1");
        Element ele2=ele1.element("name");
        System.out.println("第一个p1内部name的元素值为:"+ele2.getText());
    }

    private static void getName(Document document) {         //得到所有的name元素的值
        Element element=document.getRootElement();//获取根节点
       List  <Element> list= element.elements("p1");
        System.out.print("所有name的元素值为:");
        for (Element ele:list) {
            Element elem=ele.element("name");
            System.out.print(elem.getText()+" ");
        }
        System.out.println();
    }
    public static void xmlWriters(String path,Document document) throws Exception {
        org.dom4j.io.OutputFormat outputFormat= org.dom4j.io.OutputFormat.createPrettyPrint();//以对齐的方式显示，格式比较好看
        XMLWriter xmlWriter=new XMLWriter(new FileOutputStream(path),outputFormat);
        xmlWriter.write(document);
        xmlWriter.close();
    }
}

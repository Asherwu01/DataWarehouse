package com.asher.udtf;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDTF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.StructField;
import org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

@Description(name = "explode_json_array", value = " - this function can explode jsonArray ")
public class ExplodeJsonArray extends GenericUDTF {

    /*
        作用：
           1.对输入类型做检查
           2.返回期望类型数据的检测器
     */
    @Override
    public StructObjectInspector initialize(StructObjectInspector argOIs) throws UDFArgumentException {
        //1 对输入类型做检查
        // lateral view explode_json_array(get_json_object(line, '$.actions'))
        //1.1 参数个数必须满足
        List<? extends StructField> inputFields = argOIs.getAllStructFieldRefs();
        if (inputFields.size() != -1) {
            new UDFArgumentException("explode_json_array的参数个数必须是1，你传入的参数个数不对");
        }
        //1.2 参数类型检查
        ObjectInspector oi = inputFields.get(0).getFieldObjectInspector();
        if (oi.getCategory() != ObjectInspector.Category.PRIMITIVE || !"string".equals(oi.getTypeName())) {
            new UDFArgumentException("explode_json_array的参数类型必须是String，你传入的参数类型不对");
        }

        //2 返回期望类型数据的检测器
        List<String> names = new ArrayList<>();
        names.add("action");
        ArrayList<ObjectInspector> ois = new ArrayList<>();
        ois.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);

        return ObjectInspectorFactory.getStandardStructObjectInspector(names, ois);
    }

    /*
        处理数据 [{}, {}, {}, ...]
        forward();
     */
    @Override
    public void process(Object[] objects) throws HiveException {
        // 得到一个json字符串，解析出来，获取每个json对象forward出去
        String jsonArrayString = objects[0].toString();

        // 封装成JSONArray，对json字符层进行解析
        JSONArray jsonArray = new JSONArray(jsonArrayString);

        for (int i = 0; i < jsonArray.length(); i++) {
            String json = jsonArray.getString(i);//事实表中的一行数据
            String[] jsons = new String[1];
            jsons[0] = json;//将来炸裂成多行，每一行可能有多列
            forward(jsons);
        }

    }

    /*
        关闭资源
     */
    @Override
    public void close() throws HiveException {

    }

}

## 不想写`startActivity`

## 使用

### 要去的`Activity`里面,加个注解
```java
@StartActivityAnnotation(
        flag = Intent.FLAG_ACTIVITY_NEW_TASK,
        keys = "String,"+Constants.BUNDLE_TITLE
)
public class NextActivity extends AppCompatActivity{
}
```

### 执行`Rebuild Project`,会生成
```java
public class NextActivity_Start {
    public static void start(Context context,String bundle_title){
    Intent intent=new Intent(context,NextActivity.class);

	intent.setFlags(268435456);
	intent.putExtra("bundle_title",bundle_title);
    context.startActivity(intent);
    }
}
```
### 然后直接使用
```java
csnowstack.notwritestartactivity.NextActivity_Start.start(MainActivity.this,
                    "我不想写StartActivityForResult");
```

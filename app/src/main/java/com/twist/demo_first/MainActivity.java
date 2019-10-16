package com.twist.demo_first;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.sp_city)
    Spinner spCity;
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.tv_random)
    TextView tvRandom;
    @BindView(R.id.sp_country)
    Spinner spCountry;
    @BindView(R.id.tv_coordinate)
    TextView tvCoordinate;
    @BindView(R.id.lv_weekday)
    ListView lvWeekday;
    @BindView(R.id.tv_fruit_cn)
    TextView tvFruitCn;
    @BindView(R.id.tv_fruit_us)
    TextView tvFruitUs;
    @BindView(R.id.btn_fruit_choose)
    Button btnFruitChoose;
    @BindView(R.id.btn_login)
    Button btnLogin;

    private int color[] = {Color.GREEN, Color.BLACK, Color.BLUE, Color.WHITE, Color.YELLOW};
    private String[] weekday = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    private String[] fruitCN = {"苹果", "橘子", "香蕉"};
    private String[] fruitUS = {"Apple", "Orange", "Banana"};

    private int indexFruit = 0;

    private ArrayAdapter<CharSequence> adapter;
    private List<CharSequence> data = null;
    private ArrayList<Fruit> fruitArrayList = new ArrayList<Fruit>();
    FruitAdapter fruitAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initFruits();
        fruitAdapter = new FruitAdapter(this, R.layout.fruit_item, fruitArrayList);
        lvWeekday.setAdapter(fruitAdapter);

        spCountry.setPrompt("选择国籍:");

        data = new ArrayList<CharSequence>();
        data.add("中国");
        data.add("美国");
        data.add("日本");
        data.add("英国");

        adapter = new ArrayAdapter<CharSequence>(this, R.layout.support_simple_spinner_dropdown_item, this.data);
        spCountry.setAdapter(adapter);


        //fist demo
        tvRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvRandom.setText((int) (Math.random() * 9000 + 1000) + "");
                tvRandom.setTextColor(color[new Random().nextInt(4)]);
            }
        });

        //second demo
        spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String[] cities = getResources().getStringArray(R.array.city_labels);
                tvCity.setText(getResources().getString(R.string.city_selected_label) + cities[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String[] countries = data.toArray(new String[data.size()]);
                Toast.makeText(MainActivity.this, "您的国籍是：" + countries[i], Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        tvCoordinate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                tvCoordinate.setText("X = " + motionEvent.getX() + ", Y = " + motionEvent.getY());
                return false;
            }
        });

        btnFruitChoose.setOnClickListener(new OnClickListenerImpl());

        btnLogin.setOnClickListener(new Login());

        //lvWeekday.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, weekday));//利用ArrayAdapter对数据进行包装
        super.registerForContextMenu(lvWeekday);
    }

    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {
            btnLogin.setEnabled(false);
            btnLogin.setText(l / 1000 + getString(R.string.second));
        }

        @Override
        public void onFinish() {
            btnLogin.setEnabled(true);
            btnLogin.setText(R.string.reget);
        }
    }

    private class Login implements View.OnClickListener {

        EditText username;
        EditText password;

        String usernameString;
        String passwordString;

        //Time

        TimeCount timeCount = new TimeCount(60000, 1000);

        @Override
        public void onClick(View v) {


            timeCount.start();

            /*final ProgressDialog dialog = ProgressDialog.show(MainActivity.this, getString(R.string.search_internet), getString(R.string.searching));
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

            new Thread() {
                @Override
                public void run() {
                    super.run();

                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        dialog.dismiss();
                    }
                }
            }.start();*/

            /*Dialog dialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    btnLogin.setText(i + "年" + i1 + "月" + i2 + "日");
                }
            },2019, 10, 16);
            dialog.show();*/
            /*View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.login_dialog, null);
            username = (EditText) view.findViewById(R.id.ed_username);
            password = (EditText) view.findViewById(R.id.ed_password);

            Dialog dialog = new AlertDialog.Builder(MainActivity.this).setTitle(R.string.login).setView(view)
                    .setPositiveButton(R.string.login, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            usernameString = username.getText().toString();
                            passwordString = password.getText().toString();
                            if (usernameString.equals("twist") && passwordString.equals("123456")){
                                Toast.makeText(MainActivity.this, R.string.login_success, Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(MainActivity.this, R.string.login_failure, Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).create();
            dialog.show();*/
        }
    }

    private class OnClickListenerImpl implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            Dialog dialog = new AlertDialog.Builder(MainActivity.this)
                    .setIcon(R.drawable.ic_launcher_foreground)
                    .setTitle("选择水果")
                    .setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            MainActivity.this.tvFruitCn.setText(MainActivity.this.fruitCN[MainActivity.this.indexFruit]);
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .setSingleChoiceItems(MainActivity.this.fruitCN, 0, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            MainActivity.this.tvFruitUs.setText(MainActivity.this.fruitUS[i]);
                            MainActivity.this.indexFruit = i;
                        }
                    }).create();
            dialog.show();
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle(R.string.context_menu_title);
        menu.add(Menu.NONE, Menu.FIRST + 1, 1, R.string.delete);
        menu.add(Menu.NONE, Menu.FIRST + 2, 2, R.string.cancel);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {//选择监听
        AdapterView.AdapterContextMenuInfo acmiRef = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int removeIndex = acmiRef.position;
        switch (item.getItemId()) {
            case Menu.FIRST + 1:
                fruitArrayList.remove(removeIndex);
                adapter.notifyDataSetChanged();//删除后刷新LisView
                break;
            case Menu.FIRST + 2:
                break;
            default:
                break;
        }
        return false;
    }

    private void initFruits() {
        Fruit apple = new Fruit(R.drawable.ic_launcher_foreground, "Apple");
        fruitArrayList.add(apple);
        Fruit orange = new Fruit(R.drawable.ic_launcher_foreground, "Orange");
        fruitArrayList.add(orange);
        Fruit banana = new Fruit(R.drawable.ic_launcher_foreground, "Banana");
        fruitArrayList.add(banana);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            this.exitDialog();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exitDialog() {
        Dialog dialog = new AlertDialog.Builder(this).setTitle(R.string.exit_programmer).setMessage(R.string.exit_ques).setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MainActivity.this.finish();
            }
        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        }).create(); //创建Dialog
        dialog.show(); //显示对话框
    }

}

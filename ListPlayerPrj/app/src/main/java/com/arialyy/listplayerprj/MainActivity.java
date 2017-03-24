package com.arialyy.listplayerprj;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.Bind;
import butterknife.ButterKnife;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
  @Bind(R.id.list) RecyclerView mList;
  ListPlayAdapter mAdapter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    LinearLayoutManager manager = new LinearLayoutManager(this);
    mAdapter = new ListPlayAdapter(this, addEntity());
    mList.setLayoutManager(manager);
    mList.setAdapter(mAdapter);
  }

  private List<Entity> addEntity() {
    List<Entity> list = new ArrayList<>();
    list.add(new Entity(true, "http://172.18.104.11:80/uploadServer/upload/mp4/guaiwulaixi.mp4"));
    String[] imgs = getResources().getStringArray(R.array.imgs);
    for (String img : imgs) {
      list.add(new Entity(false, img));
    }
    return list;
  }
}

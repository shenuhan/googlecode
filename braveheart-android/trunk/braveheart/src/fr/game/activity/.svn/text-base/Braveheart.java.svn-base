package fr.game.activity;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import fr.android.api.Api;
import fr.game.R;
import fr.game.debug.EventLogger;

public class Braveheart extends ListActivity {
	private enum EnumActivity {
		BATTLEFIELD("BattleField", Battlefield.class),
		FIGHTERS("Fighters", Fighters.class),
		SPRITES("Sprites", Sprites.class),
		ENGINE("Engine Sand Box", EngineSandBox.class);

		public String name;
		public Class<? extends Activity> activity;
		private EnumActivity(String name, Class<? extends Activity> activity) {
			this.name = name;
			this.activity = activity;
		}
		@Override
		public String toString() {
			return name;
		}
	}

	private EnumActivity[] items = EnumActivity.values();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Api.start();
		EventLogger.start();

		setListAdapter(new ArrayAdapter<EnumActivity>(this, R.layout.list,items));

		ListView lv = getListView();
		lv.setTextFilterEnabled(true);

		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Braveheart.this.startActivity(new Intent(Braveheart.this, items[position].activity));
			}
		});
	}

	@Override
	protected void onDestroy() {
		Api.stop();
		super.onDestroy();
	}
}
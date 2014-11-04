package com.threerings.challenge.android;

import playn.android.GameActivity;
import playn.core.PlayN;

import com.threerings.challenge.core.Topjob;

public class TopjobActivity extends GameActivity {

  @Override
  public void main(){
    PlayN.run(new Topjob());
  }
}

package com.threerings.challenge.java;

import playn.core.PlayN;
import playn.java.JavaPlatform;

import com.threerings.challenge.core.Topjob;

public class TopjobJava {

  public static void main(String[] args) {
    JavaPlatform.Config config = new JavaPlatform.Config();
    config.width = 800;
    config.height = 600;
    // use config to customize the Java platform, if needed
    JavaPlatform.register(config);
    PlayN.run(new Topjob());
  }
}

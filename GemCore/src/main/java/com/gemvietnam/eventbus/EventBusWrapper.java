package com.gemvietnam.eventbus;


import org.greenrobot.eventbus.EventBus;

/**
 * Event bus wrapper
 * Created by NEO on 12/29/2016.
 */

public class EventBusWrapper {

  private EventBusWrapper() {

  }

  public static void register(Object subscriber) {
    EventBus.getDefault().register(subscriber);
  }

  public static void unregister(Object subscriber) {
    EventBus.getDefault().unregister(subscriber);
  }

  public static void post(Object event) {
    EventBus.getDefault().post(event);
  }
}

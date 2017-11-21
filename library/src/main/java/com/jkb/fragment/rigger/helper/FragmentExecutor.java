package com.jkb.fragment.rigger.helper;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import java.util.List;

/**
 * Used to manage fragment transaction.(builder pattern)
 *
 * @author JingYeoh
 *         <a href="mailto:yangjing9611@foxmail.com">Email me</a>
 *         <a href="https://github.com/justkiddingbaby">Github</a>
 *         <a href="http://blog.justkiddingbaby.com">Blog</a>
 * @since Nov 21,2017
 */

public class FragmentExecutor {

  /**
   * Prevents this class from being instantiated.
   */
  private FragmentExecutor() {
  }

  public static Builder beginTransaction(FragmentManager fm) {
    return new Builder(fm);
  }

  public static class Builder {

    private FragmentManager fm;
    private FragmentTransaction ft;

    public Builder(FragmentManager fm) {
      this.fm = fm;
      ft = fm.beginTransaction();
    }

    /**
     * Schedules a commit of this transaction.
     */
    public void commit() {
      ft.commit();
      ft = null;
      fm = null;
    }

    /**
     * Add a fragment to the activity state.
     *
     * @param contentId   Optional identifier of the container this fragment is
     *                    to be placed in.  If 0, it will not be placed in a container.
     * @param fragment    The fragment to be added.  This fragment must not already
     *                    be added to the activity.
     * @param fragmentTag Optional tag name for the fragment
     */
    public Builder add(@IdRes int contentId, @NonNull Fragment fragment, @NonNull String fragmentTag) {
      ft.add(contentId, fragment, fragmentTag);
      return this;
    }

    /**
     * Remove an existing fragment.  If it was added to a container, its view
     * is also removed from that container.
     */
    public Builder remove(Fragment fragment) {
      if (fragment == null) return this;
      if (fm.findFragmentByTag(fragment.getTag()) == null) return this;
      ft.detach(fragment);
      ft.remove(fragment);
      return this;
    }

    /**
     * Shows a previously hidden fragment.
     */
    public Builder show(@NonNull Fragment fragment) {
      ft.show(fragment);
      return this;
    }

    /**
     * Hides an existing fragment.
     */
    public Builder hide(Fragment fragment) {
      if (fragment == null) return this;
      if (fm.findFragmentByTag(fragment.getTag()) == null) return this;
      ft.hide(fragment);
      return this;
    }

    /**
     * Remove all fragments that is added.
     */
    public Builder clear() {
      List<Fragment> fragments = fm.getFragments();
      if (fragments == null || fragments.size() == 0) {
        return this;
      }
      for (Fragment fragment : fragments) {
        remove(fragment);
      }
      return this;
    }

    /**
     * Hide all existing fragment
     */
    public Builder hideAll() {
      List<Fragment> fragments = fm.getFragments();
      if (fragments == null || fragments.size() == 0) {
        return this;
      }
      for (Fragment fragment : fragments) {
        hide(fragment);
      }
      return this;
    }
  }
}

package com.komikatow.komiku;

import org.junit.Test;

import com.komikatow.komiku.ui.activityes.DetailActivity;
import com.komikatow.komiku.ui.fragments.BaseFragment;
import com.komikatow.komiku.ui.fragments.FragmentHome;

import java.util.Arrays;

public final class TestAllClass {


    @Test
    public void reflectClassDetail() throws NoSuchMethodException {

        //Testing kelas DetailActivity menggunakan Reflection
        Class<DetailActivity> detailReflect = DetailActivity.class;

        System.out.println(detailReflect.getSuperclass());
        System.out.println(detailReflect.getGenericSuperclass());
        System.out.println(detailReflect.getDeclaredMethod("getDetail"));
        System.out.println(Arrays.toString(detailReflect.getDeclaredMethods()));
        System.out.println(detailReflect.getTypeName());

    }

    @Test
    public void reflectClassFragmentHome() throws NoSuchMethodException {

        Class<FragmentHome> homeReflect = FragmentHome.class;

        System.out.println(homeReflect.getSuperclass());
        System.out.println(homeReflect.getGenericSuperclass());
        System.out.println(homeReflect.getDeclaredMethod("getResponseKomikJepang"));
        System.out.println(Arrays.toString(homeReflect.getInterfaces()));
        System.out.println(Arrays.toString(homeReflect.getDeclaredMethods()));
        System.out.println(homeReflect.getTypeName());

    }

    @Test
    public void reflectClassBaseFragment() throws NoSuchFieldException {

        Class<BaseFragment> baseFragmentClass = BaseFragment.class;

        System.out.println(baseFragmentClass.getTypeName());
        System.out.println(baseFragmentClass.getSuperclass());
        System.out.println(Arrays.toString(baseFragmentClass.getDeclaredMethods()));
        System.out.println(baseFragmentClass.getDeclaredField("binding"));

    }

    @Test
    public void getTimeLocale() {

        MainActivity.getTimeInLocale();
        System.out.println(MainActivity.hasilDate);
    }
}

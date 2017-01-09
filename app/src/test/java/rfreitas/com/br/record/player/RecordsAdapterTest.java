package rfreitas.com.br.record.player;

/**
 * Created by rafaelfreitas on 1/8/17.
 */

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.stubbing.answers.CallsRealMethods;
import org.mockito.stubbing.Answer;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

import java.util.Arrays;
import java.util.List;

import rfreitas.com.br.record.BuildConfig;
import rfreitas.com.br.record.record.Record;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class RecordsAdapterTest {

    private List<Record> records = Arrays.asList(new Record("/root", "item1", 0), new Record("/root", "item2", 0));

    @Mock
    private Context context;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void whenTheListOfRecordsIsNullTheMethodGetItemCountMustBeReturnZero(){
        RecordsAdapter adapter = new RecordsAdapter(context, null);
        assertEquals(0, adapter.getItemCount());
    }

}

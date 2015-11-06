package edu.rutgers.cs.rahul.helloworld;

import java.util.ArrayList;
import com.google.api.services.youtube.model.PlaylistItem;
/**
 * Created by Rahul on 05-11-2015.
 */

public class PlayList {
    public class PlayListElement
    {
        String VIDEO_ID;
        int BPM;
        PlaylistItem ITEM;
        PlayListElement(String vid, int beat)
        {
            VIDEO_ID = vid;
            BPM = beat;
            ITEM = null;
        }
        PlayListElement()
        {
            VIDEO_ID = "";
            BPM = 0;
            ITEM = null;
        }
        PlayListElement(String vid, int beat, PlaylistItem it)
        {
            VIDEO_ID = vid;
            BPM = beat;
            ITEM = it;
        }


    };
    private static ArrayList<PlayListElement> list;
    public PlayList()
    {
        list = new ArrayList<PlayListElement>();
    }
    public void add(String video_id, int bpm)
    {
        PlayListElement elem = new PlayListElement(video_id, bpm);
        list.add(elem);
    }
    public void add(String video_id, int bpm, PlaylistItem it)
    {
        PlayListElement elem = new PlayListElement(video_id, bpm, it);
        list.add(elem);
    }
    public void add(String video_id)
    {
        PlayListElement elem = new PlayListElement(video_id, 0);
        list.add(elem);
    }
    public void clearList()
    {
        list.clear();
    }

    public int size()
    {
        return list.size();
    }

    public String get(int index)
    {
        return list.get(index).VIDEO_ID;
    }

    public PlayListElement get_element(int index)
    {
        return list.get(index);
    }

    public PlaylistItem get_playlist_item(int index)
    {
        return list.get(index).ITEM;
    }

    public int indexOf(String video_id)
    {
        for(int i = 0; i<list.size(); ++i)
        {
            if(list.get(i).VIDEO_ID.equals(video_id))
                return i;
        }
        return -1;
    }
};

package com.laioffer.twitch.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.laioffer.twitch.db.entity.ItemEntity;
import com.laioffer.twitch.external.model.Video;
import com.laioffer.twitch.external.model.Clip;
import com.laioffer.twitch.external.model.Stream;
import com.laioffer.twitch.model.ItemType;

import java.util.ArrayList;
import java.util.List;

public record TypeGroupedItemList(
        @JsonProperty("stream_list") List<ItemEntity> streams,
        @JsonProperty("video_list") List<ItemEntity> videos,
        @JsonProperty("clip_list") List<ItemEntity> clips
) {
    public TypeGroupedItemList(List<ItemEntity> items) {
        /*
        you are entering a list of unordered items, use filterForType to filter out different items
        * Stream,
        * Video,
        * Clip for constructing the object
         */
        this(
                filterForType(items, ItemType.STREAM),
                filterForType(items, ItemType.VIDEO),
                filterForType(items, ItemType.CLIP)
        );
    }


    // gameId is given by the twitch API
    public TypeGroupedItemList(String gameId, List<Stream> streams, List<Video> videos, List<Clip> clips) {
        this(
                fromStreams(streams),
                fromVideos(gameId, videos),
                fromClips(clips)
        );
    }

    private static List<ItemEntity> filterForType(List<ItemEntity> items, ItemType type) {
        List<ItemEntity> filtered = new ArrayList<>();
        for (ItemEntity item : items) {
            if (item.type() == type) {
                filtered.add(item);
            }
        }
        return filtered;
    }


    private static List<ItemEntity> fromStreams(List<Stream> streams) {
        List<ItemEntity> items = new ArrayList<>();
        for (Stream stream : streams) {
            items.add(new ItemEntity(stream));
        }
        return items;
    }


    private static List<ItemEntity> fromVideos(String gameId, List<Video> videos) {
        List<ItemEntity> items = new ArrayList<>();
        for (Video video : videos) {
            items.add(new ItemEntity(gameId, video));
        }
        return items;
    }


    private static List<ItemEntity> fromClips(List<Clip> clips) {
        List<ItemEntity> items = new ArrayList<>();
        for (Clip clip : clips) {
            items.add(new ItemEntity(clip));
        }
        return items;
    }

}
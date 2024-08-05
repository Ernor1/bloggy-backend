package rw.global.qt.bloggy.services;

import rw.global.qt.bloggy.dtos.requests.CreateTagDTO;
import rw.global.qt.bloggy.models.Tag;

import java.util.List;
import java.util.UUID;

public interface ITagService {
    public Tag createTag(CreateTagDTO tag);
    public Tag getTagById(UUID id);
    public Tag updateTag(UUID id, CreateTagDTO tag);
    public void deleteTag(UUID id);
    public Tag getTagByName(String name);
    public List<Tag> getAllTags();
}

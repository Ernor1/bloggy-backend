package rw.global.qt.bloggy.services.serviceImpls;

import org.springframework.stereotype.Service;
import rw.global.qt.bloggy.dtos.requests.CreateTagDTO;
import rw.global.qt.bloggy.models.Tag;
import rw.global.qt.bloggy.services.ITagService;

import java.util.List;
import java.util.UUID;

@Service
public class TagServiceImpl implements ITagService {
    @Override
    public Tag createTag(CreateTagDTO tag) {
        return null;
    }

    @Override
    public Tag getTagById(UUID id) {
        return null;
    }

    @Override
    public Tag updateTag(UUID id, CreateTagDTO tag) {
        return null;
    }

    @Override
    public void deleteTag(UUID id) {

    }

    @Override
    public Tag getTagByName(String name) {
        return null;
    }

    @Override
    public List<Tag> getAllTags() {
        return null;
    }
}

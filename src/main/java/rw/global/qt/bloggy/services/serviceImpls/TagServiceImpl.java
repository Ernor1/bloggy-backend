package rw.global.qt.bloggy.services.serviceImpls;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import rw.global.qt.bloggy.dtos.requests.CreateTagDTO;
import rw.global.qt.bloggy.exceptions.ResourceNotFoundException;
import rw.global.qt.bloggy.models.Tag;
import rw.global.qt.bloggy.repositories.ITagRepository;
import rw.global.qt.bloggy.services.ITagService;
import rw.global.qt.bloggy.utils.ExceptionUtils;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TagServiceImpl implements ITagService {
    private final ITagRepository tagRepository;
    @Override
    public Tag createTag(CreateTagDTO tag) {
       try {
           Tag newTag = new Tag(tag.getName(), tag.getDescription());
              return tagRepository.save(newTag);
       }catch (Exception e){
           e.printStackTrace();
           ExceptionUtils.handleServiceExceptions(e);
           return null;
       }
    }

    @Override
    public Tag getTagById(UUID id) {
        try {
            return tagRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tag not found"));
        } catch (Exception e) {
            ExceptionUtils.handleServiceExceptions(e);
            return null;
        }
    }

    @Override
    public Tag updateTag(UUID id, CreateTagDTO tag) {
        try {
            Tag tagToUpdate = tagRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tag not found"));
            tagToUpdate.setName(tag.getName());
            tagToUpdate.setDescription(tag.getDescription());
            return tagRepository.save(tagToUpdate);}
        catch (Exception e){
            ExceptionUtils.handleServiceExceptions(e);
            return null;
        }
    }

    @Override
    public void deleteTag(UUID id) {
        try {
            Tag tagToDelete = tagRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tag not found"));
            tagRepository.delete(tagToDelete);
        } catch (Exception e) {
            ExceptionUtils.handleServiceExceptions(e);
        }

    }

    @Override
    public Tag getTagByName(String name) {
        try {
            return tagRepository.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Tag not found"));
        } catch (Exception e) {
            ExceptionUtils.handleServiceExceptions(e);
            return null;
        }
    }

    @Override
    public List<Tag> getAllTags() {
        try {
            return tagRepository.findAll();
        } catch (Exception e) {
            ExceptionUtils.handleServiceExceptions(e);
            return null;
        }
    }
}

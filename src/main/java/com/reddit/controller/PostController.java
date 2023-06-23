package com.reddit.controller;

import com.reddit.dto.CommentDto;
import com.reddit.entity.Draft;
import com.reddit.service.CommunityService;
import com.reddit.service.DraftService;
import com.reddit.service.FileService;
import com.reddit.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Controller
public class PostController {
    @Autowired
    FileService fileService;
    @Autowired
    PostService postService;
    @Autowired
    DraftController draftController;
    @Autowired
    DraftService draftService;
    @Autowired
    CommunityService communityService;

    @Value("${project.image}")
    private String path;

    @GetMapping("/community-post")
    public String communityPost(@RequestParam(value = "communityName") String communityName, Model model) {
        model.addAttribute("communityName", communityName);
        model.addAttribute("communityList",communityService.findAllCommunities());
        return "NewPost";
    }

    @GetMapping("/new-post")
    public String viewPost(@RequestParam(value = "communityName",required = false)String communityName,Principal principal,Model model) {
        model.addAttribute("communityName",communityName);
        model.addAttribute("draftedPosts", draftService.findAllDraftedPosts().size());
        model.addAttribute("communityList",communityService.findAllCommunities());
        return "NewPost";
    }

    @PostMapping("/save-post")
    public String fileUpload(Principal principal,
                             @RequestParam(value = "communityName", required = false) String communityName,
                             @RequestParam(value = "link draft", required = false) String linkDraft,
                             @RequestParam(value = "post-draft", required = false) String postDraft,
                             @RequestParam(value = "draft", required = false) String draft,
                             @RequestParam(value = "title") String title,
                             @RequestParam(value = "image", required = false) List<MultipartFile> images,
                             @RequestParam(value = "content", required = false) String content,
                             @RequestParam(value = "url", required = false) String url,
                             @RequestParam(value = "poll", required = false) String poll,
                             @RequestParam(value = "update draft", required = false) String updateDraftLink,
                             @RequestParam(value = "cancel", required = false) String cancelButton,
                             @RequestParam(value = "draftId", required = false) UUID draftId,
                             Model model) throws IOException {
        if (cancelButton != null && (!cancelButton.isEmpty())) {
            return "redirect:/new-post";
        } else if (updateDraftLink != null && updateDraftLink.equals("update Draft")) {
            draftService.updateDraftLink(title, url, draftId,principal);
            return "redirect:/draft";
        } else if (linkDraft != null && linkDraft.equals("save Draft")) {
            draftService.draftPostUrl(title, url,principal);
            return "redirect:/draft";
        } else if (postDraft != null && postDraft.equals("Post")) {
            postService.saveDraftedPost(title, content, draftId, url,principal,communityName);
            draftService.deleteDraftById(draftId);
            return "file-response";
        } else if (draftId != null) {
            draftController.updateDraft(draftId, title, content,principal);
            return "redirect:/draft";
        } else if (draft != null && !draft.isEmpty()) {
            draftController.saveDraftPost(title, content, model,principal);
            List<Draft> draftPosts = draftService.findAllDraftedPosts();
            model.addAttribute("draftedPosts", draftPosts);
            return "draft";
        } else{
            postService.post(title,images,url,content,principal,communityName,path);
        }
        return "redirect;/home";
    }

    @GetMapping("/media/{filename:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        Resource file = fileService.load(path, filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @GetMapping("/{typeOfAccount}/{username}/comments/{postId}")
    public String viewPost(@PathVariable Long postId,
                           @PathVariable String typeOfAccount,
                           @PathVariable String username,
                           Model model) {
        model.addAttribute("postData", postService.getPostByType(typeOfAccount, username, postId));
        model.addAttribute("commentDto", new CommentDto());
        return "view-post";
    }
}
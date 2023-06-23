package com.reddit.controller;

import com.reddit.entity.Draft;
import com.reddit.service.CommunityService;
import com.reddit.service.DraftService;
import com.reddit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Controller
public class DraftController {
    @Autowired
    DraftService draftService;
    @Autowired
    CommunityService communityService;
    @Autowired
    UserService userService;

    @GetMapping("/draft")
    public String viewDraft(Model model,Principal principal){
        List<Draft> draftPosts = draftService.findAllDraftedPosts();
        model.addAttribute("draftedPosts",userService.getByUsername(principal.getName()).getUserDrafts());
        return "draft";
    }
    @PostMapping("/draft-posts")
    public String saveDraftPost(String title, String content, Model model, Principal principal){
        System.out.println(principal.getName());
        draftService.saveDraft(title,content,principal);
        return "redirect:/draft";
    }

    @GetMapping("/delete/draft")
    public String deleteDraftPost(@RequestParam(value = "draftId") UUID draftId){
        draftService.deleteDraftById(draftId);
        return "redirect:/draft";
    }

    @GetMapping("/edit/draft")
    public String editDraft(@RequestParam(value = "draftId") UUID draftId,Model model){
        Draft draft = draftService.getDraftById(draftId);
        model.addAttribute("editDraft",draft);
        model.addAttribute("communityList",communityService.findAllCommunities());
        List<Draft> draftPosts = draftService.findAllDraftedPosts();
        model.addAttribute("draftedPosts",draftPosts.size());
        return "edit";
    }
    @GetMapping("/update/draft")
    public void updateDraft(UUID draftId, String title, String content, Principal principal){
        draftService.updateDraftById(draftId,title,content,principal);
    }
}
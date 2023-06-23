package com.reddit.service;

import com.reddit.entity.Community;
import com.reddit.entity.User;
import com.reddit.repository.CommunityRepository;
import com.reddit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class CommunityService {

    @Autowired
    CommunityRepository communityRepository;
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    public Community saveNewCommunity(Principal principal, Community community, String radio) {
        community.setCommunityName(community.getCommunityName().replaceAll(" ",""));
        Long userId = userRepository.findByUsername(principal.getName()).getUserId();
        Set<Community> ownedCommunities = new HashSet<>();
        User user=userService.getUserByID(userId);
        community.setOwnerId(user);
        if(radio.equals("restricted")){
            community.setIsPrivate(false);
            community.setIsRestrict(true);
        }
        else if(radio.equals("private")){
            community.setIsPrivate(true);
            community.setIsRestrict(false);
        }
        else{
            community.setIsPrivate(false);
            community.setIsRestrict(false);
        }
        Set<User> userSet = new HashSet<>();
        userSet.add(user);
        community.setCommunityMembers(userSet);
        community.setCommunityModerators(userSet);
        Community community1 = communityRepository.save(community);
        ownedCommunities.add(community1);
        user.setOwnedCommunities(ownedCommunities);
        userRepository.save(user);
        return community1;
    }

    public Community findCommunityByCommunityName(String communityName) {
        return communityRepository.findByCommunityName(communityName);
    }

    public boolean isCommunityNameExists(String communityName) {
        return communityRepository.existsByCommunityName(communityName);
    }

    public void addMemberToModerator(Community community, User user) {
        Set<User> moderators = community.getCommunityModerators();
        moderators.add(user);
        community.setCommunityModerators(moderators);
        communityRepository.save(community);
    }

    public void removeMemberFromModerator(Community community, User user) {
        Set<User> moderators = community.getCommunityModerators();
        moderators.remove(user);
        community.setCommunityModerators(moderators);
        communityRepository.save(community);
    }

    public void removeUserFromCommunity(Community community, User user) {
        Set<User> communityMembers = community.getCommunityMembers();
        Set<User> communityModerators = community.getCommunityModerators();
        communityMembers.remove(user);
        communityModerators.remove(user);
        communityRepository.save(community);
    }

    public List<Community> findAllCommunities() {
        return communityRepository.findAll();
    }

    public Community getByName(String name) {
        return communityRepository.findByCommunityName(name);
    }

    public void joinUserIntoCommunity(Community community, User user) {
        if(!community.getOwnerId().getUserId().equals(user.getUserId()))
        {
            Set<User> members = community.getCommunityMembers();
            members.add(user);
            communityRepository.save(community);
        }
    }
}
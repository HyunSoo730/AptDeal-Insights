package com.ssafy.finalproject.like.controller;

import com.ssafy.finalproject.aptsale.entity.AptSale;
import com.ssafy.finalproject.like.entity.LikeApt;
import com.ssafy.finalproject.like.service.LikeAptService;
import com.ssafy.finalproject.member.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LikeAptController {

    @Autowired
    private LikeAptService likeAptService;

    @PostMapping("/like")
    public ResponseEntity<LikeApt> likeApartment(@RequestBody LikeApt likeApt) {
        LikeApt savedLikeApt = likeAptService.saveLikeApt(likeApt);
        return ResponseEntity.ok(savedLikeApt);
    }


    @GetMapping("/likes")
    public ResponseEntity<List<AptSale>> getLikedAptsByMemberId(@RequestParam Long memberId) {
        List<AptSale> likedAptsByMemberId = likeAptService.findLikedAptsByMemberId(memberId);
        return ResponseEntity.ok(likedAptsByMemberId);
    }
}

package com.example.chatapplication.model.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MoviesHomeView {
    private List<MovieView> movies = new ArrayList<>();
    private List<MovieView> series = new ArrayList<>();
    private List<MovieView> popular = new ArrayList<>();
    private List<MovieView> anime = new ArrayList<>();
    private List<MovieView> newest = new ArrayList<>();
}

package com.example.studentrelief.services.model;

import java.util.List;

public class records {
    public List<DonnerModel> getDonnerModels() {
        return donnerModels;
    }

    public void setDonnerModels(List<DonnerModel> donnerModels) {
        this.donnerModels = donnerModels;
    }

    List<DonnerModel> donnerModels;
}

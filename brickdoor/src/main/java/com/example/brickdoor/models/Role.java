package com.example.brickdoor.models;

public enum Role {
  STUDENT(0),
  COMPANY(1),
  ADMIN(2);

  private int id;

  Role(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }
}

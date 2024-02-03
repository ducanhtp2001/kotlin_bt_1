package com.example.kotlin_1.Model

data class SinhVien(var name: String, var age : Int) {
    public override fun toString(): String {
        return "Họ tên: " + name + ", tuổi: " + age + ".";
    }
    public fun plusAge() {
        age++
    }
    public fun minusAge() {
        age--
    }
}
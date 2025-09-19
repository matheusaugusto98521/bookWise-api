package br.com.mtech.bookWise.DTO;

public record BookRequestDTO(String title, String isbn, int numberPages, int yearPublication) {
}

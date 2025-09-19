package br.com.mtech.bookWise.DTO.response;

public record ResponseDTO<T>(String message, T data) {
}

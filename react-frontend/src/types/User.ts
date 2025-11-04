export interface UserRequestDto {
  username: string;
  email: string;
  password: string;
  role?: string; // optional, settes ofte av backend
}

export interface UserResponseDto {
  id: number;
  username: string;
  email: string;
  role: string;
}
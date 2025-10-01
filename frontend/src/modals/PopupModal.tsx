"use client";
import { ReactNode, useEffect } from "react";

interface ModalProps {
  open: boolean;
  onClose: () => void;
  title: string;
  message: string | ReactNode;
  autoClose?: number; // ms, optional
}

export default function ConfirmationModal({ open, onClose, title, message, autoClose }: ModalProps) {
  useEffect(() => {
    if (open && autoClose) {
      const t = setTimeout(() => onClose(), autoClose);
      return () => clearTimeout(t);
    }
  }, [open, autoClose, onClose]);

  if (!open) return null;

  return (
    <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
      <div className="bg-gray-800 p-6 rounded shadow-lg max-w-sm w-full text-center">
        <h2 className="text-xl font-bold mb-4">{title}</h2>
        <p className="mb-4">{message}</p>
        <button
          onClick={onClose}
          className="px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700"
        >
          OK
        </button>
      </div>
    </div>
  );
}

import styles from './Modal.module.scss';

type ModalProps = {
  title?: string;
  onClose: () => void;
  show: boolean;
  children: React.ReactNode;
  onConfirm?: () => void;      // optional confirm handler
  onCancel?: () => void;       // optional cancel handler
  confirmLabel?: string;       // optional confirm button text
  cancelLabel?: string;        // optional cancel button text
  isSuccess?: boolean;
};

const Modal = ({
  title,
  onClose,
  show,
  children,
  onConfirm,
  onCancel,
  confirmLabel = "Confirm",
  cancelLabel = "Cancel",
  isSuccess = false,
}: ModalProps) => {
  if (!show) return null;

  // Default cancel action falls back to onClose if onCancel not provided
  const handleCancel = () => {
    if (onCancel) onCancel();
    else onClose();
  };

  return (
    <div className={styles.overlay}>
      <div className={`${styles.modal} ${isSuccess ? styles.success : styles.error}`}>
        {title && <h2>{title}</h2>}

        {children}

        <div style={{ marginTop: "1.5rem", display: "flex", justifyContent: "center" }}>
          {onConfirm && (
            <button
              className={styles.confirmBtn}
              onClick={onConfirm}
            >
              {confirmLabel}
            </button>
          )}

          
            <button
              className={styles.cancelBtn}
              onClick={handleCancel}
              style={{ marginLeft: onConfirm ? "1rem" : undefined }}
            >
              {cancelLabel}
            </button>
        </div>
      </div>
    </div>
  );
};

export default Modal;

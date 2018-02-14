package home.maintenance.view;

public interface Views {
    interface AuditView {
        interface AuditDate {
            interface CreatedDate {}
            interface LastModifiedDate {}
        }
        interface AuditUser {
            interface CreatedBy {}
            interface LastModifiedBy {}
        }
    }
    interface UserView {
        interface Name extends AuditView.AuditDate.CreatedDate, AuditView.AuditUser.CreatedBy {}
        interface UI extends Name {}
    }
}

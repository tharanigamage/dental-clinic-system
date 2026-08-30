<div class="modal fade" id="confirmActionModal" tabindex="-1">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title text-warning"><i class="bi bi-exclamation-triangle me-2"></i>Please Confirm</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
                <p id="confirmActionMessage" class="mb-0"></p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                <button type="button" class="btn btn-danger" id="confirmActionButton">Confirm</button>
            </div>
        </div>
    </div>
</div>
<script>
    function showConfirm(message, onConfirm) {
        document.getElementById('confirmActionMessage').textContent = message;
        document.getElementById('confirmActionButton').onclick = function () {
            bootstrap.Modal.getOrCreateInstance(document.getElementById('confirmActionModal')).hide();
            onConfirm();
        };
        bootstrap.Modal.getOrCreateInstance(document.getElementById('confirmActionModal')).show();
    }

    document.getElementById('confirmActionModal').addEventListener('hidden.bs.modal', function () {
        if (!document.querySelector('.modal.show')) {
            document.body.classList.remove('modal-open');
            document.body.style.removeProperty('overflow');
            document.body.style.removeProperty('padding-right');
            document.querySelectorAll('.modal-backdrop').forEach(function (backdrop) {
                backdrop.remove();
            });
        }
    });
</script>
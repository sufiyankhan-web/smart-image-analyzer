function LoadingSpinner({ label = 'Processing image...' }) {
  return (
    <div className="flex items-center gap-3 text-brand-muted">
      <div className="h-5 w-5 animate-spin rounded-full border-2 border-brand-primary/40 border-t-brand-primary" />
      <span className="text-sm">{label}</span>
    </div>
  );
}

export default LoadingSpinner;

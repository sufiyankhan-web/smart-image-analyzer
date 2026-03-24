function ImagePreviewCard({ src, name }) {
  return (
    <div className="glass-card overflow-hidden">
      {src ? (
        <img
          src={src}
          alt={name || 'Uploaded image'}
          className="h-72 w-full object-cover"
        />
      ) : (
        <div className="flex h-72 items-center justify-center border border-white/5 bg-brand-panelSoft/40 text-xs text-brand-muted">
          No image loaded
        </div>
      )}
      {name && (
        <div className="border-t border-white/5 px-4 py-2.5">
          <p className="truncate text-xs text-brand-muted">{name}</p>
        </div>
      )}
    </div>
  );
}

export default ImagePreviewCard;
